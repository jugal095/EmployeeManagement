package com.jugal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jugal.exception.CEOAlreadyExistsException;
import com.jugal.exception.CannotMoveEmployeeException;
import com.jugal.exception.DepartmentNotFoundException;
import com.jugal.exception.EmployeeNotFoundException;
import com.jugal.exception.ManagerNotFoundException;
import com.jugal.exception.NewEmployeeException;
import com.jugal.model.Department;
import com.jugal.model.Employee;
import com.jugal.model.EmployeeSummary;
import com.jugal.repository.DepartmentRepository;
import com.jugal.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository empRepo;

	@Autowired
	private DepartmentRepository deptRepo;

	private static final String CEO_TITLE = "CEO";

	public List<EmployeeSummary> getEmployeesByDept(Long deptId) {
		Department d = deptRepo.findById(deptId).orElseThrow(() -> new DepartmentNotFoundException("Dept not found"));
		return empRepo.findByDepartment(d).stream()
				.map(e -> new EmployeeSummary(e.getId(), e.getName(), e.getEmail(), e.getTitle())).toList();
	}

	public List<EmployeeSummary> getReportsToManager(Long managerId) {
		Employee m = empRepo.findById(managerId).orElseThrow(() -> new ManagerNotFoundException("Manager not found"));
		return empRepo.findByManager(m).stream()
				.map(e -> new EmployeeSummary(e.getId(), e.getName(), e.getEmail(), e.getTitle())).toList();
	}

	@Transactional
	public Employee addEmployee(Employee e) {
		// CEO rules
		if (CEO_TITLE.equals(e.getTitle())) {
			// CEO must have no manager and no department
			e.setManager(null);
			e.setDepartment(null);
			// ensure no other CEO
			Employee existing = getCEO();
			if (existing != null)
				throw new CEOAlreadyExistsException("CEO already exists");
			return empRepo.save(e);
		}

		// non-CEO: must have dept and manager
		if (e.getDepartment() == null)
			throw new NewEmployeeException("Employee must belong to a department");
		if (e.getManager() == null)
			throw new NewEmployeeException("Employee must have a manager");

		// manager must belong to same dept OR be CEO? rules: employee and manager must
		// belong to same dept. CEO doesn't belong to dept; but dept heads report to
		// CEO, so manager of dept head is CEO (CEO title). So allow manager==CEO only
		// if employee is dept head? We'll just allow manager==CEO only if employee's
		// title contains "Head" or employee is set as department.head.

		Employee manager = empRepo.findById(e.getManager().getId())
				.orElseThrow(() -> new ManagerNotFoundException("Manager not found"));

		// if manager is CEO
		if (CEO_TITLE.equals(manager.getTitle())) {
			// ensure this employee will be department head (we expect caller sets dept.head
			// later) OR allow only one direct report from same dept to CEO (i.e., dept head
			// only)
			// Check that no other employee from same dept already directly reports to CEO
			List<Employee> ceoDirect = empRepo.findByManager(manager);
			boolean existsSameDept = ceoDirect.stream().anyMatch(
					x -> x.getDepartment() != null && x.getDepartment().getId().equals(e.getDepartment().getId()));
			if (existsSameDept)
				throw new NewEmployeeException("Another employee from this department already reports directly to CEO");
		} else {
			// manager must belong to same dept
			if (manager.getDepartment() == null || !manager.getDepartment().getId().equals(e.getDepartment().getId())) {
				throw new NewEmployeeException("Manager must belong to same department as employee");
			}
		}
		return empRepo.save(e);
	}

	private Employee getCEO() {
		return empRepo.findAll().stream().filter(e -> CEO_TITLE.equals(e.getTitle())).findFirst().orElse(null);
	}

	@Transactional
	public Employee moveEmployee(Long empId, Long targetDeptId) {
		Employee emp = empRepo.findById(empId).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
		if (CEO_TITLE.equals(emp.getTitle()))
			throw new CannotMoveEmployeeException("Cannot move CEO");

		Department target = deptRepo.findById(targetDeptId)
				.orElseThrow(() -> new DepartmentNotFoundException("Department not found"));

		// manager must be in same dept as employee; so if employee's manager is not in
		// target dept, move invalid
		Employee manager = emp.getManager();
		if (manager == null)
			throw new CannotMoveEmployeeException("Employee has no manager");
		if (!CEO_TITLE.equals(manager.getTitle())) {
			if (manager.getDepartment() == null || !manager.getDepartment().getId().equals(target.getId())) {
				throw new CannotMoveEmployeeException(
						"Cannot move employee to target department because current manager is not in that department");
			}
		} else {
			// manager is CEO -> moving employee to different dept while manager is CEO
			// means ensure no other direct-report from new dept to CEO
			List<Employee> ceoDirect = empRepo.findByManager(manager);
			boolean existsSameDept = ceoDirect.stream().anyMatch(x -> x.getDepartment() != null
					&& x.getDepartment().getId().equals(target.getId()) && !x.getId().equals(emp.getId()));
			if (existsSameDept)
				throw new CannotMoveEmployeeException("Target department already has a direct report to CEO");
		}

		// if employee is department head of current dept, prevent move (or clear head).
		// To be safe, refuse move unless not dept head.
		Department curr = emp.getDepartment();
		if (curr != null && curr.getHead() != null && curr.getHead().getId().equals(emp.getId())) {
			throw new CannotMoveEmployeeException(
					"Cannot move department head. Demote head first or change department head.");
		}

		emp.setDepartment(target);
		return empRepo.save(emp);
	}
}

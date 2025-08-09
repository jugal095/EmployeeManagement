# EmployeeManagement

API Endpoints:
POST /api/departments  { "name": "X" }
POST /api/employees    { "name":"..", "email":"..", "title":"..", "department":{ "id": <deptId> }, "manager":{ "id": <managerId> } }
POST /api/employees/{id}/move/{deptId}
GET  /api/departments/{id}/employees
GET  /api/managers/{id}/reports

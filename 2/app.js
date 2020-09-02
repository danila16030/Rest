var Company = /** @class */ (function () {
    function Company() {
        this.employeeList = [];
    }
    Company.prototype.add = function (employee) {
        this.employeeList.push(employee);
    };
    Company.prototype.getProjectList = function () {
        var result;
        result = [];
        this.employeeList.forEach(function (employee) { return result.push(employee.getProjectName()); });
        return result;
    };
    Company.prototype.getNameList = function () {
        var result;
        result = [];
        this.employeeList.forEach(function (employee) { return result.push(employee.getName()); });
        return result;
    };
    return Company;
}());
var Frontend = /** @class */ (function () {
    function Frontend(name, projectName) {
        this.name = name;
        this.projectName = projectName;
    }
    Frontend.prototype.getName = function () {
        return this.name;
    };
    Frontend.prototype.getProjectName = function () {
        return this.projectName;
    };
    return Frontend;
}());
var Backend = /** @class */ (function () {
    function Backend(name, projectName) {
        this.name = name;
        this.projectName = projectName;
    }
    Backend.prototype.getName = function () {
        return this.name;
    };
    Backend.prototype.getProjectName = function () {
        return this.projectName;
    };
    return Backend;
}());
var company = new Company();
company.add(new Frontend("1", "first"));
company.add(new Frontend("2", "first"));
company.add(new Backend("3", "second"));
company.add(new Backend("4", "second"));
console.log(company.getNameList());
console.log(company.getProjectList());

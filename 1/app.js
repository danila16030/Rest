var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (Object.prototype.hasOwnProperty.call(b, p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var Employee = /** @class */ (function () {
    function Employee(name, projectName) {
        this.projectName = projectName;
        this.name = name;
    }
    Employee.prototype.getName = function () {
        return this.name;
    };
    Employee.prototype.getProjectName = function () {
        return this.projectName;
    };
    return Employee;
}());
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
var Frontend = /** @class */ (function (_super) {
    __extends(Frontend, _super);
    function Frontend() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    return Frontend;
}(Employee));
var Backend = /** @class */ (function (_super) {
    __extends(Backend, _super);
    function Backend() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    return Backend;
}(Employee));
var company = new Company();
company.add(new Frontend("1", "first"));
company.add(new Frontend("2", "first"));
company.add(new Backend("3", "second"));
company.add(new Backend("4", "second"));
console.log(company.getNameList());
console.log(company.getProjectList());

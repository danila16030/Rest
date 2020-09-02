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
var CompanyLocationLocalStorage = /** @class */ (function () {
    function CompanyLocationLocalStorage() {
        this.storage = [];
        localStorage.setItem('storage', JSON.stringify(this.storage));
    }
    CompanyLocationLocalStorage.prototype.addPerson = function (person) {
        var storage = JSON.parse(localStorage.getItem('storage'));
        storage.push(person);
        localStorage.setItem('storage', JSON.stringify(storage));
    };
    CompanyLocationLocalStorage.prototype.getCount = function () {
        var storage = JSON.parse(localStorage.getItem('storage'));
        return storage.length;
    };
    CompanyLocationLocalStorage.prototype.getPerson = function (id) {
        var storage = JSON.parse(localStorage.getItem('storage'));
        return storage[id];
    };
    return CompanyLocationLocalStorage;
}());
var LocalCompany = /** @class */ (function () {
    function LocalCompany() {
        this.employeeList = new CompanyLocationLocalStorage();
    }
    LocalCompany.prototype.add = function (employee) {
        this.employeeList.addPerson(employee);
    };
    LocalCompany.prototype.getProjectList = function () {
        var result = [];
        var count = this.employeeList.getCount();
        for (var a = 0; a < count; a++) {
            result.push(this.employeeList.getPerson(a)["projectName"]);
        }
        return result;
    };
    LocalCompany.prototype.getNameList = function () {
        var result = [];
        var count = this.employeeList.getCount();
        for (var a = 0; a < count; a++) {
            result.push(this.employeeList.getPerson(a)["name"]);
        }
        return result;
    };
    return LocalCompany;
}());
var CompanyLocationArray = /** @class */ (function () {
    function CompanyLocationArray() {
        this.array = [];
    }
    CompanyLocationArray.prototype.addPerson = function (person) {
        this.array.push(person);
    };
    CompanyLocationArray.prototype.getCount = function () {
        return this.array.length;
    };
    CompanyLocationArray.prototype.getPerson = function (id) {
        return this.array[id];
    };
    return CompanyLocationArray;
}());
var ArrayCompany = /** @class */ (function () {
    function ArrayCompany() {
        this.employeeList = new CompanyLocationArray();
    }
    ArrayCompany.prototype.add = function (employee) {
        this.employeeList.addPerson(employee);
    };
    ArrayCompany.prototype.getProjectList = function () {
        var result = [];
        var count = this.employeeList.getCount();
        for (var a = 0; a < count; a++) {
            result.push(this.employeeList.getPerson(a).getProjectName());
        }
        return result;
    };
    ArrayCompany.prototype.getNameList = function () {
        var result = [];
        var count = this.employeeList.getCount();
        for (var a = 0; a < count; a++) {
            result.push(this.employeeList.getPerson(a).getName());
        }
        return result;
    };
    return ArrayCompany;
}());
var localCompany = new LocalCompany();
var arrayCompany = new ArrayCompany();
localCompany.add(new Employee("1 local", "first local"));
localCompany.add(new Employee("2 local", "second local"));
arrayCompany.add(new Employee("1 array", "first array"));
arrayCompany.add(new Employee("2 array", "second array"));
console.log(localCompany.getNameList());
console.log(localCompany.getProjectList());
console.log(arrayCompany.getNameList());
console.log(arrayCompany.getProjectList());

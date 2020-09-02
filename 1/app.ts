class Employee {
    name: string;
    projectName: string;

    constructor(name: string, projectName: string) {
        this.projectName = projectName;
        this.name = name;
    }

    getName(): string {
        return this.name;
    }

    getProjectName(): string {
        return this.projectName;
    }
}

class Company {
    employeeList: Employee[] = [];

    add(employee: Employee): void {
        this.employeeList.push(employee);
    }

    getProjectList(): string[] {
        let result: string[];
        result = [];
        this.employeeList.forEach(employee => result.push(employee.getProjectName()))
        return result;
    }

    getNameList(): string[] {
        let result: string[];
        result = [];
        this.employeeList.forEach(employee => result.push(employee.getName()));
        return result;
    }
}

class Frontend extends Employee {

}

class Backend extends Employee {

}

let company = new Company();
company.add(new Frontend("1", "first"));
company.add(new Frontend("2", "first"));
company.add(new Backend("3", "second"));
company.add(new Backend("4", "second"));
console.log(company.getNameList());
console.log(company.getProjectList());
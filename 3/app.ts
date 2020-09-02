interface ILocation {
    addPerson(person: Employee): void;

    getPerson(id: number): Employee;

    getCount(): number;
}

class Employee {
    private readonly name: string;
    private readonly projectName: string;

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

class CompanyLocationLocalStorage implements ILocation {
    storage: Employee[] = [];

    constructor() {
        localStorage.setItem('storage', JSON.stringify(this.storage));
    }

    addPerson(person: Employee): void {
        let storage = JSON.parse(localStorage.getItem('storage'));
        storage.push(person);
        localStorage.setItem('storage', JSON.stringify(storage));
    }

    getCount(): number {
        let storage = JSON.parse(localStorage.getItem('storage'));
        return storage.length;
    }

    getPerson(id: number): Employee {
        let storage = JSON.parse(localStorage.getItem('storage'));
        return storage[id];
    }
}

class LocalCompany {
    employeeList = new CompanyLocationLocalStorage();

    add(employee: Employee): void {
        this.employeeList.addPerson(employee);
    }

    getProjectList(): string[] {
        let result: string[] = [];
        let count = this.employeeList.getCount();
        for (let a = 0; a < count; a++) {
            result.push(this.employeeList.getPerson(a)["projectName"])
        }
        return result;
    }

    getNameList(): string[] {
        let result: string[] = [];
        let count = this.employeeList.getCount();
        for (let a = 0; a < count; a++) {
            result.push(this.employeeList.getPerson(a)["name"])
        }
        return result;
    }
}

class CompanyLocationArray implements ILocation {

    array: Array<Employee> = [];

    addPerson(person: Employee): void {
        this.array.push(person);
    }

    getCount(): number {
        return this.array.length;
    }

    getPerson(id: number): Employee {
        return this.array[id];
    }

}

class ArrayCompany {
    employeeList = new CompanyLocationArray();

    add(employee: Employee): void {
        this.employeeList.addPerson(employee);
    }

    getProjectList(): string[] {
        let result: string[] = [];
        let count = this.employeeList.getCount();
        for (let a = 0; a < count; a++) {
            result.push(this.employeeList.getPerson(a).getProjectName());
        }
        return result;
    }

    getNameList(): string[] {
        let result: string[] = [];
        let count = this.employeeList.getCount();
        for (let a = 0; a < count; a++) {
            result.push(this.employeeList.getPerson(a).getName());
        }
        return result;
    }
}


let localCompany = new LocalCompany();
let arrayCompany = new ArrayCompany();
localCompany.add(new Employee("1 local", "first local"));
localCompany.add(new Employee("2 local", "second local"));

arrayCompany.add(new Employee("1 array", "first array"));
arrayCompany.add(new Employee("2 array", "second array"));

console.log(localCompany.getNameList());
console.log(localCompany.getProjectList());

console.log(arrayCompany.getNameList());
console.log(arrayCompany.getProjectList());
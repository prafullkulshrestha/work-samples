export class Employee {
    id: number;
    firstName: string;
    lastName: string;
    gender: string;
    dateOfBirth: string;
    departmentName: string;

    constructor( id?: number,
        firstName?: string,
        lastName?: string,
        gender?: string,
        dateOfBirth?: string,
        departmentName?: string){
        
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.gender = gender;
            this.dateOfBirth = dateOfBirth;
            this.departmentName = departmentName;
    }
}
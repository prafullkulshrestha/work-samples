export class Employee {
    id: number;
    firstName: string;
    lastName: string;
    gender: string;
    dateOfBirth: string;
    department: string;

    constructor( id?: number,
        firstName?: string,
        lastName?: string,
        gender?: string,
        dateOfBirth?: string,
        department?: string) {

            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.gender = gender;
            this.dateOfBirth = dateOfBirth;
            this.department = department;
    }
}

import { AbstractControl, ValidatorFn } from '@angular/forms';
import * as moment from 'moment';

export class CustomValidationService {
//    static checkLimit(min: number, max: number): ValidatorFn {
//     return (c: AbstractControl): { [key: string]: boolean } | null => {
//         if (c.value && (isNaN(c.value) && !(c.value.length > min && c.value.length < max))) {
//             return { 'limit': true };
//         }
        
//         return null;
//     };
 // }
  static validateAdult () {
    return (c: AbstractControl): { [key: string]: boolean } | null => {
           var inputDate = moment(c.value);
           var eighteenYearsBack = moment(new Date()).subtract(18, 'years');
           var sixtyYearsBack = moment(new Date()).subtract(60, 'years');
                if (eighteenYearsBack.isBefore(inputDate) || inputDate.isBefore(sixtyYearsBack)) {
                    return { 'dateRangeError': true };
                }
                
                return null;
  }
}
}
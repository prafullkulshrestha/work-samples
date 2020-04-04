import { AppLogger } from '../../shared/utilities/logging/AppLogger';
import { Response } from '@angular/http';
import { v4 as uuid } from 'uuid';
import {HttpResponse, HttpHeaders, HttpClient} from '@angular/common/http';
import { throwError, Observable } from 'rxjs';
const win: any = window;

export abstract class ServiceBase {
	public headers: { headers: HttpHeaders };

	constructor() {
	}

	public handleError(error: Response) {
		let errorText: string;
		if (!error.text || error.text() === null || error.text() === undefined) {
			errorText = 'Error Not Found';
		} else {
			errorText = error.status.toString() + ' : ' + error.text();
		}
		AppLogger.error('An error occurred ' + errorText);
		return throwError(error);
	}

	public handleLogout(message: string) {
		console.log(message);
	}
}

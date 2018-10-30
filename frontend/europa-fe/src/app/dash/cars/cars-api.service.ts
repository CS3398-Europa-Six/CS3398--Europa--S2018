import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import { Car } from './car';
import 'rxjs/add/observable/throw';
import { throwError } from 'rxjs';

const httpOptions = {
     headers: new HttpHeaders({
      'Content-Type':  'application/json',
     })
   };


@Injectable({
  providedIn: 'root'
})
export class CarsApiService {

  constructor(private http: HttpClient) { }
  private static _handleError(err: HttpErrorResponse | any) {
      return throwError(err.message || 'Error: Unable to complete request.');
  }
  getCars(): Observable<any>{
      return this.http
         .get<any>("http://45.33.13.200:5000/cars")
	 .catch(CarsApiService._handleError);
  }

}

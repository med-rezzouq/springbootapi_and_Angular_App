import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, of, tap } from 'rxjs';
import { UserStorageService } from 'src/app/services/storage/user-storage.service';
import { environment } from 'src/environments/environment';

const BASIC_URL = environment['BASIC_URL'];

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient,) { }

  addProduct(productDto: any): Observable<any> {
    return this.http
      .post<[]>(BASIC_URL + "api/admin/product", productDto, {
        headers: this.createAuthorizationHeader(),
      })
      .pipe(
        tap((_) => this.log('Product posted successfully')),
        catchError(this.handleError<[]>('Error posting Product', []))
      );
  }

  getProductById(productId: any): Observable<any> {
    return this.http
      .get<[]>(BASIC_URL + `api/admin/product/${productId}`, {
        headers: this.createAuthorizationHeader(),
      })
      .pipe(
        tap((_) => this.log('Product Fetched successfully')),
        catchError(this.handleError<[]>('Error Getting Product', []))
      );
  }

  updateProduct(productId: any, productDto: any): Observable<any> {
    return this.http
      .put<[]>(BASIC_URL + `api/admin/product/${productId}`, productDto, {
        headers: this.createAuthorizationHeader(),
      })
      .pipe(
        tap((_) => this.log('Product updated successfully')),
        catchError(this.handleError<[]>('Error updating Product', []))
      );
  }

  deleteProductById(productId: any): Observable<any> {
    return this.http
      .delete<[]>(BASIC_URL + `api/admin/product/${productId}`, {
        headers: this.createAuthorizationHeader(),
        observe: 'response'
      })
      .pipe(
        tap((_) => this.log('Product Deleted successfully')),
        catchError(this.handleError<[]>('Error Deleting Product', []))
      );
  }

  getProductsByTitle(title: any): Observable<any> {
    return this.http
      .get<[]>(BASIC_URL + `api/admin/search/${title}`, {
        headers: this.createAuthorizationHeader(),
      })
      .pipe(
        tap((_) => this.log('Products fetched successfully')),
        catchError(this.handleError<[]>('Error getting Products', []))
      );
  }

  getAllProducts(): Observable<any> {
    return this.http
      .get<[]>(BASIC_URL + `api/admin/products`, {
        headers: this.createAuthorizationHeader(),
      })
      .pipe(
        tap((_) => this.log('Products fetched successfully')),
        catchError(this.handleError<[]>('Error getting Products', []))
      );
  }

  getAllUsers(): Observable<any> {
    return this.http
      .get<[]>(BASIC_URL + `api/admin/users`, {
        headers: this.createAuthorizationHeader(),
      })
      .pipe(
        tap((_) => this.log('Users fetched successfully')),
        catchError(this.handleError<[]>('Error getting users', []))
      );
  }

  deleteUserById(userId: any): Observable<any> {
    return this.http
      .delete<[]>(BASIC_URL + `api/admin/user/${userId}`, {
        headers: this.createAuthorizationHeader(),
      })
      .pipe(
        tap((_) => this.log('User Deleted successfully')),
        catchError(this.handleError<[]>('Error Deleting user', []))
      );
  }

  getPlacedOrders(): Observable<any> {
    return this.http
      .get<[]>(BASIC_URL + `api/admin/placedOrders`, {
        headers: this.createAuthorizationHeader(),
      })
      .pipe(
        tap((_) => this.log('Orders fetched successfully')),
        catchError(this.handleError<[]>('Error getting Orders', []))
      );
  }

  createAuthorizationHeader(): HttpHeaders {
    let authHeaders: HttpHeaders = new HttpHeaders();
    return authHeaders.set(
      'Authorization',
      'Bearer ' + UserStorageService.getToken()
    );
  }

  log(message: string): void {
    console.log(`Admin Service: ${message}`);
  }

  handleError<T>(operation = 'operation', result?: T): any {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}

import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {ProfileResponse} from "../models/profile-response";
import {FormGroup} from "@angular/forms";
import {Router} from "@angular/router";
import {ProfileRequest} from "../models/profile-request";

const API_URL = 'http://localhost:8080/api/user/';


@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient, private router: Router) {
  }

  getProfile(id: string): Observable<ProfileResponse> {
    return this.http.get<ProfileResponse>(API_URL + id);
  }

  deleteProfile(id: string) {
    this.http.delete(API_URL + id).subscribe();
  }


  putProfile(form: FormGroup, id: string) {
    const request: ProfileRequest = {
      username: form.get('name')?.value,
      email: form.get('email')?.value,
      password: form.get('password')?.value
    };

    this.http.put<any>(API_URL + id, request)
      .subscribe(null, null, () => {
        this.router.navigate(["/details-profile/" + id]);
      });
  }
}

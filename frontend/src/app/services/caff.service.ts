import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, ReplaySubject} from "rxjs";
import {FormGroup} from "@angular/forms";
import {CaffResponse} from "../models/caff-response";
import {TokenStorageService} from "./token-storage.service";
import {CaffRequest} from "../models/caff-request";
import {Router} from "@angular/router";
import {CaffDownloadResponse} from "../models/caff-download-response";

const API_URL = 'http://localhost:8080/api/caff/';

@Injectable({
  providedIn: 'root'
})
export class CaffService {

  constructor(private http: HttpClient, private tokenStorageService: TokenStorageService, private router: Router) {
  }

  getCaffsList(): Observable<CaffResponse[]> {
    return this.http.get<CaffResponse[]>(API_URL + 'list');
  }

  getCaffsListSearch(uploaderName: string, name: string): Observable<CaffResponse[]> {
    if (uploaderName != "" && name != "") {
      return this.http.get<CaffResponse[]>(API_URL + 'list', {
        params: {
          uploaderName: uploaderName,
          name: name
        }
      });
    }
    if (name != "") {
      return this.http.get<CaffResponse[]>(API_URL + 'list', {
        params: {
          name: name
        }
      });
    }
    if (uploaderName != "") {
      return this.http.get<CaffResponse[]>(API_URL + 'list', {
        params: {
          uploaderName: uploaderName
        }
      });
    }
    return this.http.get<CaffResponse[]>(API_URL + 'list');
  }

  getMyCaffsList()
    :
    Observable<CaffResponse[]> {
    const userId = this.tokenStorageService.getUser().id;
    return this.http.get<CaffResponse[]>(API_URL + userId + '/caffs');
  }

  getCaff(id
            :
            string
  ):
    Observable<CaffResponse> {
    return this.http.get<CaffResponse>(API_URL + id);
  }

  convertFile(file
                :
                File
  ):
    Observable<string> {
    const result = new ReplaySubject<string>(1);
    const reader = new FileReader();
    reader.readAsBinaryString(file);
    reader.onload = (event) => {
      if (event.target && event.target.result) {
        result.next(btoa(event.target.result.toString()));
      }
    };
    return result;
  }

  testUpload(file: File, myForm: FormGroup) {
    const formData = new FormData();

    let base64Output: string;
    this.convertFile(file).subscribe((base64) => {
      base64Output = base64;

      let request: CaffRequest = {
        name: myForm.get('name')?.value,
        description: myForm.get('description')?.value,
        imageBase64: base64Output
      }

      console.log(JSON.stringify(formData.get("file")));
      let id: string;
      this.http.post<CaffResponse>(API_URL + "upload", request)
        .subscribe(res => {
            console.log(res);
            id = res.id;
          },
          null,
          () => this.router.navigate([`details-caff/${id}`]));
    }, null, null);
  }

  downloadFile(id: string) {

    const endpoint = API_URL + id + "/download";
    this.http.get<CaffDownloadResponse>(endpoint).subscribe(data => {
      let caffBinary = atob(data.caffBase64);
      let enc = new TextEncoder();
      let myBlob = new Blob([new Uint8Array(enc.encode(caffBinary))]);
      let link = document.createElement('a');
      link.download = "caff_download.caff";
      link.href = window.URL.createObjectURL(myBlob);
      link.click();
    });
  }


}

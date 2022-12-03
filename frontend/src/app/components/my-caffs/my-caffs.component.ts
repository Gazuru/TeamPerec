import {Component, OnInit} from '@angular/core';
import {CaffService} from "../../services/caff.service";
import {CaffResponse} from "../../models/caff-response";
import {TokenStorageService} from "../../services/token-storage.service";
import {EMPTY, Observable} from "rxjs";

@Component({
  selector: 'app-my-caffs',
  templateUrl: './my-caffs.component.html',
  styleUrls: ['./my-caffs.component.css']
})
export class MyCaffsComponent implements OnInit {
  caffs: Observable<CaffResponse[]> = EMPTY;

  constructor(private caffService: CaffService, private tokenStorageService: TokenStorageService) {

  }

  ngOnInit(): void {
    this.getMyCaffsList();
  }

  getMyCaffsList() {
    this.caffs = this.caffService.getMyCaffsList();
  }

}

import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProfileService} from "../../services/profile.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ProfileResponse} from "../../models/profile-response";

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {
  profile: ProfileResponse = {
    id: "",
    name: "",
    email: "",
    downloads: [],
    caffs: []
  };

  myForm = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.minLength(8)]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(8)])
  });

  constructor(private route: ActivatedRoute, private profileService: ProfileService) {
  }

  sub: any;
  id: any;

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
      this.profileService.getProfile(this.id).subscribe(data => {
        this.profile = data;
      });
    });
  }

  get f() {
    return this.myForm.controls;
  }

  submit() {
    this.profileService.putProfile(this.myForm, this.id);
  }
}

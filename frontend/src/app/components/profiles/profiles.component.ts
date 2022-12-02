import {Component, OnInit} from '@angular/core';
import {ProfileService} from "../../services/profile.service";
import {ProfileResponse} from "../../models/profile-response";
import {TokenStorageService} from "../../services/token-storage.service";

@Component({
  selector: 'app-profiles',
  templateUrl: './profiles.component.html',
  styleUrls: ['./profiles.component.css']
})
export class ProfilesComponent implements OnInit {
  profiles: ProfileResponse[] = [];
  showAdmin = false;

  constructor(private profileService: ProfileService, private tokenStorageService: TokenStorageService) {
  }

  ngOnInit(): void {
    this.profileService.getProfiles().subscribe(data => {
      this.profiles = data;
      console.log(this.profiles)
    });
    this.showAdmin = this.tokenStorageService.getUser().roles.includes('ROLE_ADMIN');
  }

  deleteProfile(id: string) {
    this.profileService.deleteProfile(id);
    window.location.reload();
  }
}

import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProfileService} from "../../services/profile.service";
import {TokenStorageService} from "../../services/token-storage.service";
import {ProfileDownloadResponse} from "../../models/profile-download-response";
import {ProfileCaffResponse} from "../../models/profile-caff-response";
import {ProfileResponse} from "../../models/profile-response";
import {EMPTY, map, Observable} from "rxjs";

@Component({
  selector: 'app-details-profile',
  templateUrl: './details-profile.component.html',
  styleUrls: ['./details-profile.component.css']
})
export class DetailsProfileComponent implements OnInit {
  profile: Observable<ProfileResponse> = EMPTY;
  caffs: Observable<ProfileCaffResponse[]> = EMPTY;
  downloads: Observable<ProfileDownloadResponse[]> = EMPTY;

  //downloads:DownloadResponse[]=[];
  id: string = "";
  showDeleteButton = false;


  private sub: any;

  constructor(private route: ActivatedRoute, private profileService: ProfileService, private tokenStorageService: TokenStorageService) {
  }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
      this.profile = this.profileService.getProfile(this.id);
      this.caffs = this.profileService.getProfile(this.id).pipe(map(profile => profile.caffs));
      this.downloads = this.profileService.getProfile(this.id).pipe(map(profile => profile.downloads));
      this.showDeleteButton = this.tokenStorageService.getUser().roles.includes("ROLE_ADMIN");
    });
  }


  deleteProfile() {
    this.profileService.deleteProfile(this.id);
  }
}

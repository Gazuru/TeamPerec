import {Component, Input, OnInit} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-caff-card',
  templateUrl: './caff-card.component.html',
  styleUrls: ['./caff-card.component.css']
})
export class CaffCardComponent implements OnInit {
  @Input() caff: any;
  previewGifImage: any;

  constructor(private sanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    this.previewGifImage = this.sanitizer.bypassSecurityTrustResourceUrl(`data:image/gif;base64, ${this.caff.imagePreviewBase64}`);

  }

}

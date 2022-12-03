import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-downloaded-caff-card',
  templateUrl: './downloaded-caff-card.component.html',
  styleUrls: ['./downloaded-caff-card.component.css']
})
export class DownloadedCaffCardComponent implements OnInit {
  @Input() download: any;

  constructor() {
  }

  ngOnInit(): void {
  }

}

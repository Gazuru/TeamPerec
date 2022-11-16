import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DownloadedCaffCardComponent } from './downloaded-caff-card.component';

describe('DownloadedCaffCardComponent', () => {
  let component: DownloadedCaffCardComponent;
  let fixture: ComponentFixture<DownloadedCaffCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DownloadedCaffCardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DownloadedCaffCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

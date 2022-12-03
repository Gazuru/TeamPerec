import {ComponentFixture, TestBed} from '@angular/core/testing';

import {UploadCaffComponent} from './upload-caff.component';

describe('UploadCaffComponent', () => {
  let component: UploadCaffComponent;
  let fixture: ComponentFixture<UploadCaffComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UploadCaffComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(UploadCaffComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

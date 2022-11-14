import {ComponentFixture, TestBed} from '@angular/core/testing';

import {DetailsCaffComponent} from './details-caff.component';

describe('DetailsCaffComponent', () => {
  let component: DetailsCaffComponent;
  let fixture: ComponentFixture<DetailsCaffComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DetailsCaffComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(DetailsCaffComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

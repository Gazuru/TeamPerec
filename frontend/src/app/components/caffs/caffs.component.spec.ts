import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CaffsComponent} from './caffs.component';

describe('CaffsComponent', () => {
  let component: CaffsComponent;
  let fixture: ComponentFixture<CaffsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CaffsComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(CaffsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

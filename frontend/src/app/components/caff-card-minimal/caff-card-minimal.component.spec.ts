import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CaffCardMinimalComponent } from './caff-card-minimal.component';

describe('CaffCardMinimalComponent', () => {
  let component: CaffCardMinimalComponent;
  let fixture: ComponentFixture<CaffCardMinimalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CaffCardMinimalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CaffCardMinimalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

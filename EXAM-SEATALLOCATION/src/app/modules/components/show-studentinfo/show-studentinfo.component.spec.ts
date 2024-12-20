import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowStudentinfoComponent } from './show-studentinfo.component';

describe('ShowStudentinfoComponent', () => {
  let component: ShowStudentinfoComponent;
  let fixture: ComponentFixture<ShowStudentinfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ShowStudentinfoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ShowStudentinfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GoogleFormComponent } from './google-form.component';

describe('GoogleFormComponent', () => {
  let component: GoogleFormComponent;
  let fixture: ComponentFixture<GoogleFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GoogleFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GoogleFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

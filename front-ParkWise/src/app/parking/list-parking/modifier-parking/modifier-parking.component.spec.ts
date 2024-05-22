import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifierParkingComponent } from './modifier-parking.component';

describe('ModifierParkingComponent', () => {
  let component: ModifierParkingComponent;
  let fixture: ComponentFixture<ModifierParkingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModifierParkingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModifierParkingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifierPlaceparkingComponent } from './modifier-placeparking.component';

describe('ModifierPlaceparkingComponent', () => {
  let component: ModifierPlaceparkingComponent;
  let fixture: ComponentFixture<ModifierPlaceparkingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModifierPlaceparkingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModifierPlaceparkingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

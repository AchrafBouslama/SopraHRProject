import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AjouterPlaceparkingComponent } from './ajouter-placeparking.component';

describe('AjouterPlaceparkingComponent', () => {
  let component: AjouterPlaceparkingComponent;
  let fixture: ComponentFixture<AjouterPlaceparkingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AjouterPlaceparkingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AjouterPlaceparkingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

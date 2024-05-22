import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AjouterParkingComponent } from './ajouter-parking.component';

describe('AjouterParkingComponent', () => {
  let component: AjouterParkingComponent;
  let fixture: ComponentFixture<AjouterParkingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AjouterParkingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AjouterParkingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

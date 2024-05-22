import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AjouterEtageComponent } from './ajouter-etage.component';

describe('AjouterEtageComponent', () => {
  let component: AjouterEtageComponent;
  let fixture: ComponentFixture<AjouterEtageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AjouterEtageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AjouterEtageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

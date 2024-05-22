import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifierEtageComponent } from './modifier-etage.component';

describe('ModifierEtageComponent', () => {
  let component: ModifierEtageComponent;
  let fixture: ComponentFixture<ModifierEtageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModifierEtageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModifierEtageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChangeRequestMasterDetailComponent } from './change-request-master-detail.component';

describe('Component Tests', () => {
  describe('ChangeRequestMaster Management Detail Component', () => {
    let comp: ChangeRequestMasterDetailComponent;
    let fixture: ComponentFixture<ChangeRequestMasterDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ChangeRequestMasterDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ changeRequestMaster: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ChangeRequestMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChangeRequestMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load changeRequestMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.changeRequestMaster).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ActivityMasterDetailComponent } from './activity-master-detail.component';

describe('Component Tests', () => {
  describe('ActivityMaster Management Detail Component', () => {
    let comp: ActivityMasterDetailComponent;
    let fixture: ComponentFixture<ActivityMasterDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ActivityMasterDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ activityMaster: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ActivityMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ActivityMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load activityMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.activityMaster).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});

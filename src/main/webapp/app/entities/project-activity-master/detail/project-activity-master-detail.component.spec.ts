import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProjectActivityMasterDetailComponent } from './project-activity-master-detail.component';

describe('Component Tests', () => {
  describe('ProjectActivityMaster Management Detail Component', () => {
    let comp: ProjectActivityMasterDetailComponent;
    let fixture: ComponentFixture<ProjectActivityMasterDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ProjectActivityMasterDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ projectActivityMaster: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ProjectActivityMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectActivityMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load projectActivityMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectActivityMaster).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ProjectActivityMasterService } from '../service/project-activity-master.service';

import { ProjectActivityMasterComponent } from './project-activity-master.component';

describe('Component Tests', () => {
  describe('ProjectActivityMaster Management Component', () => {
    let comp: ProjectActivityMasterComponent;
    let fixture: ComponentFixture<ProjectActivityMasterComponent>;
    let service: ProjectActivityMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ProjectActivityMasterComponent],
      })
        .overrideTemplate(ProjectActivityMasterComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectActivityMasterComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ProjectActivityMasterService);

      const headers = new HttpHeaders().append('link', 'link;link');
      jest.spyOn(service, 'query').mockReturnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.projectActivityMasters?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

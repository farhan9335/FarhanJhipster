import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ActivityMasterService } from '../service/activity-master.service';

import { ActivityMasterComponent } from './activity-master.component';

describe('Component Tests', () => {
  describe('ActivityMaster Management Component', () => {
    let comp: ActivityMasterComponent;
    let fixture: ComponentFixture<ActivityMasterComponent>;
    let service: ActivityMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ActivityMasterComponent],
      })
        .overrideTemplate(ActivityMasterComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActivityMasterComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ActivityMasterService);

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
      expect(comp.activityMasters?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ChangeRequestMasterService } from '../service/change-request-master.service';

import { ChangeRequestMasterComponent } from './change-request-master.component';

describe('Component Tests', () => {
  describe('ChangeRequestMaster Management Component', () => {
    let comp: ChangeRequestMasterComponent;
    let fixture: ComponentFixture<ChangeRequestMasterComponent>;
    let service: ChangeRequestMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ChangeRequestMasterComponent],
      })
        .overrideTemplate(ChangeRequestMasterComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChangeRequestMasterComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ChangeRequestMasterService);

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
      expect(comp.changeRequestMasters?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

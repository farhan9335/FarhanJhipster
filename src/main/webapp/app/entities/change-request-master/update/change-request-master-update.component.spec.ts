jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ChangeRequestMasterService } from '../service/change-request-master.service';
import { IChangeRequestMaster, ChangeRequestMaster } from '../change-request-master.model';

import { ChangeRequestMasterUpdateComponent } from './change-request-master-update.component';

describe('Component Tests', () => {
  describe('ChangeRequestMaster Management Update Component', () => {
    let comp: ChangeRequestMasterUpdateComponent;
    let fixture: ComponentFixture<ChangeRequestMasterUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let changeRequestMasterService: ChangeRequestMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ChangeRequestMasterUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ChangeRequestMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChangeRequestMasterUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      changeRequestMasterService = TestBed.inject(ChangeRequestMasterService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const changeRequestMaster: IChangeRequestMaster = { id: 456 };

        activatedRoute.data = of({ changeRequestMaster });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(changeRequestMaster));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ChangeRequestMaster>>();
        const changeRequestMaster = { id: 123 };
        jest.spyOn(changeRequestMasterService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ changeRequestMaster });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: changeRequestMaster }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(changeRequestMasterService.update).toHaveBeenCalledWith(changeRequestMaster);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ChangeRequestMaster>>();
        const changeRequestMaster = new ChangeRequestMaster();
        jest.spyOn(changeRequestMasterService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ changeRequestMaster });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: changeRequestMaster }));
        saveSubject.complete();

        // THEN
        expect(changeRequestMasterService.create).toHaveBeenCalledWith(changeRequestMaster);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ChangeRequestMaster>>();
        const changeRequestMaster = { id: 123 };
        jest.spyOn(changeRequestMasterService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ changeRequestMaster });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(changeRequestMasterService.update).toHaveBeenCalledWith(changeRequestMaster);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});

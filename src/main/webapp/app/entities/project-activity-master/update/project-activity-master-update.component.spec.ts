jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ProjectActivityMasterService } from '../service/project-activity-master.service';
import { IProjectActivityMaster, ProjectActivityMaster } from '../project-activity-master.model';

import { ProjectActivityMasterUpdateComponent } from './project-activity-master-update.component';

describe('Component Tests', () => {
  describe('ProjectActivityMaster Management Update Component', () => {
    let comp: ProjectActivityMasterUpdateComponent;
    let fixture: ComponentFixture<ProjectActivityMasterUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let projectActivityMasterService: ProjectActivityMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ProjectActivityMasterUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ProjectActivityMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectActivityMasterUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      projectActivityMasterService = TestBed.inject(ProjectActivityMasterService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const projectActivityMaster: IProjectActivityMaster = { id: 456 };

        activatedRoute.data = of({ projectActivityMaster });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(projectActivityMaster));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ProjectActivityMaster>>();
        const projectActivityMaster = { id: 123 };
        jest.spyOn(projectActivityMasterService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ projectActivityMaster });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: projectActivityMaster }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(projectActivityMasterService.update).toHaveBeenCalledWith(projectActivityMaster);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ProjectActivityMaster>>();
        const projectActivityMaster = new ProjectActivityMaster();
        jest.spyOn(projectActivityMasterService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ projectActivityMaster });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: projectActivityMaster }));
        saveSubject.complete();

        // THEN
        expect(projectActivityMasterService.create).toHaveBeenCalledWith(projectActivityMaster);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ProjectActivityMaster>>();
        const projectActivityMaster = { id: 123 };
        jest.spyOn(projectActivityMasterService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ projectActivityMaster });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(projectActivityMasterService.update).toHaveBeenCalledWith(projectActivityMaster);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});

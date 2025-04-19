package edu.kantseryk.project;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.ArchTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.plantuml.rules.PlantUmlArchCondition.Configuration.consideringAllDependencies;


@SpringBootTest
class ProjArchitectureTests {

    private JavaClasses applicationClasses;

    @BeforeEach
    void initialize() {
        applicationClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("edu.kantseryk.project");
    }

    @Test
    void controllersShouldBeAnnotatedWithRestController() {
        classes()
                .that().resideInAPackage("..controller..")
                .should().beAnnotatedWith(RestController.class)
                .check(applicationClasses);
    }

    @Test
    void controllersShouldBeNamedController() {
        classes()
                .that().resideInAPackage("..controller..")
                .should().haveSimpleNameEndingWith("Controller")
                .check(applicationClasses);
    }

    @Test
    void servicesShouldBeAnnotatedWithService() {
        classes()
                .that().resideInAPackage("..service..")
                .should().beAnnotatedWith(Service.class)
                .check(applicationClasses);
    }

    @Test
    void servicesShouldBeNamedService() {
        classes()
                .that().resideInAPackage("..service..")
                .should().haveSimpleNameEndingWith("Service")
                .check(applicationClasses);
    }

    @Test
    void repositoriesShouldBeInterfaces() {
        classes()
                .that().resideInAPackage("..repository..")
                .should().beInterfaces()
                .check(applicationClasses);
    }

    @Test
    void repositoriesShouldBeAnnotatedWithRepository() {
        classes()
                .that().resideInAPackage("..repository..")
                .should().beAnnotatedWith(Repository.class)
                .check(applicationClasses);
    }

    @Test
    void repositoriesShouldBeNamedRepository() {
        classes()
                .that().resideInAPackage("..repository..")
                .should().haveSimpleNameEndingWith("Repository")
                .check(applicationClasses);
    }

    @Test
    void servicesShouldNotDependOnControllers() {
        noClasses()
                .that().resideInAPackage("..service..")
                .should().dependOnClassesThat().resideInAPackage("..controller..")
                .check(applicationClasses);
    }

    @Test
    void repositoriesShouldNotDependOnServices() {
        noClasses()
                .that().resideInAPackage("..repository..")
                .should().dependOnClassesThat().resideInAPackage("..service..")
                .check(applicationClasses);
    }

    @Test
    void controllersShouldNotDependOnEachOther() {
        noClasses()
                .that().resideInAPackage("..controller..")
                .should().dependOnClassesThat().resideInAPackage("..controller..")
                .check(applicationClasses);
    }

    @Test
    void modelFieldsShouldBePrivate() {
        fields()
                .that().areDeclaredInClassesThat().resideInAPackage("..model..")
                .should().bePrivate()
                .check(applicationClasses);
    }

    @Test
    void modelsShouldNotDependOnServices() {
        noClasses()
                .that().resideInAPackage("..model..")
                .should().dependOnClassesThat().resideInAPackage("..service..")
                .check(applicationClasses);
    }

    @Test
    void servicesFieldsShouldBePrivate() {
        fields()
                .that().areDeclaredInClassesThat().resideInAPackage("..service..")
                .should().bePrivate()
                .check(applicationClasses);
    }

    @Test
    void repositoriesShouldBeInterfacesNamedProperly() {
        classes()
                .that().resideInAPackage("..repository..")
                .should().beInterfaces()
                .andShould().haveSimpleNameEndingWith("Repository")
                .check(applicationClasses);
    }


    @Test
    void controllersFieldsShouldNotBeAutowired() {
        noFields()
                .that().areDeclaredInClassesThat().resideInAPackage("..controller..")
                .should().beAnnotatedWith(Autowired.class)
                .check(applicationClasses);
    }

    @Test
    void servicesShouldBeClasses() {
        classes()
                .that().resideInAPackage("..service..")
                .should().notBeInterfaces()
                .check(applicationClasses);
    }

    @Test
    void modelsShouldBeClasses() {
        classes()
                .that().resideInAPackage("..model..")
                .should().notBeInterfaces()
                .check(applicationClasses);
    }

    @Test
    void controllersShouldBeInCorrectPackage() {
        classes()
                .that().areAnnotatedWith(RestController.class)
                .should().resideInAPackage("..controller..")
                .check(applicationClasses);
    }

    @Test
    void servicesShouldBeInCorrectPackage() {
        classes()
                .that().areAnnotatedWith(Service.class)
                .should().resideInAPackage("..service..")
                .check(applicationClasses);
    }

    @Test
    void repositoriesShouldBeInCorrectPackage() {
        classes()
                .that().areAnnotatedWith(Repository.class)
                .should().resideInAPackage("..repository..")
                .check(applicationClasses);
    }
}
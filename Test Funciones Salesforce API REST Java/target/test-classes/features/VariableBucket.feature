Feature: Objeto variable bucket

  Scenario: Autocompletado del campo bucket
    When el usuario crea el registro variable bucket
    Then se deberia autocompletar el campo Bucket
    When el usuario actualiza el registro de variable bucket
    Then se deberia actualizar el campo Bucket


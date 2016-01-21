'use strict';

angular.module('ligaApp')
    .controller('EquipoDetailController', function ($scope, $rootScope, $stateParams, entity, Equipo, Jugador) {
        $scope.equipo = entity;
        $scope.load = function (id) {
            Equipo.get({id: id}, function(result) {
                $scope.equipo = result;
            });
        };
        $rootScope.$on('ligaApp:equipoUpdate', function(event, result) {
            $scope.equipo = result;
        });
    });

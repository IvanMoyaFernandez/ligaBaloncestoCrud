'use strict';

angular.module('ligaApp')
    .controller('JugadorDetailController', function ($scope, $rootScope, $stateParams, entity, Jugador, Equipo) {
        $scope.jugador = entity;
        $scope.load = function (id) {
            Jugador.get({id: id}, function(result) {
                $scope.jugador = result;
            });
        };
        $rootScope.$on('ligaApp:jugadorUpdate', function(event, result) {
            $scope.jugador = result;
        });
    });

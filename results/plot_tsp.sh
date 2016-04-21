#!/bin/bash

if [ $# -ne 2 ]
then
	echo "Formato:"
	echo "$0 <fichero entrada> <fichero salida>"
	exit
fi

FICHERO_ENTRADA="'$1'"
FICHERO_SALIDA="'$2'"

cat << _end_ | gnuplot
set terminal postscript eps color
set output $FICHERO_SALIDA
set key left top
set xlabel "Tiempo (ns)"
set ylabel "Max. fitness"
plot $FICHERO_ENTRADA using 1:2 w l, 'TSP02.txt' using 1:2 w l, 'TSP03.txt' using 1:2 w l
_end_

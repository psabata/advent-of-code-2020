package info.petrsabata.advent2020

import info.petrsabata.advent2020.Day17.*

fun main() {

    val activeCubes3D = mutableListOf<Position3D>()
    val activeCubes4D = mutableListOf<Position4D>()

    InputHelper("day17-1.txt").readLines().forEachIndexed { lineIndex, line ->
        line.forEachIndexed { columnIndex, char ->
            if (char == '#') {
                activeCubes3D.add(Position3D(columnIndex, lineIndex, 0))
                activeCubes4D.add(Position4D(columnIndex, lineIndex, 0, 0))
            }
        }
    }

    val energySource3D = EnergySource(activeCubes3D)

    val result3D = energySource3D.bootUp(6)

    println("There are ${result3D.size} active cubes after 6 3D cycles.") // 346

    val energySource4D = EnergySource(activeCubes4D)

    val result = energySource4D.bootUp(6)

    println("There are ${result.size} active cubes after 6 4D cycles.") // 1632


}

class Day17 {

    class EnergySource(input: List<Position>) {

        private val input = input.toSet()

        init {
            println("There are ${input.size} active cubes at the beginning: $input")
        }

        fun bootUp(cycles: Int): Set<Position> {

            var activeCubes = input

            for (cycle in 1..cycles) {

                activeCubes = cycle(activeCubes)

                println("There are ${activeCubes.size} active cubes after cycle $cycle.")
            }

            return activeCubes


        }

        private fun cycle(activeCubes: Set<Position>): Set<Position> {

            val result = mutableSetOf<Position>()

            activeCubes.flatMapTo(mutableSetOf(), { mutableSetOf(it).union(it.neighbours()) }).forEach {
                val countOfActiveNeighbours = activeCubes.intersect(it.neighbours()).size

                if (activeCubes.contains(it)) {
                    if (countOfActiveNeighbours in 2..3) {
                        result.add(it)
                    }
                } else {
                    if (countOfActiveNeighbours == 3) {
                        result.add(it)
                    }
                }
            }

            return result
        }

    }

    interface Position {

        fun neighbours(): Set<Position>

    }

    data class Position3D(val x: Int, val y: Int, val z: Int): Position {

        override fun neighbours(): Set<Position3D> {
            val neighbours = mutableSetOf<Position3D>()

            for (dx in -1..1) {
                for (dy in -1..1) {
                    for (dz in -1..1) {
                        if (dx != 0 || dy != 0 || dz != 0) {
                            neighbours.add(Position3D(x + dx, y + dy, z + dz))
                        }
                    }
                }
            }

            return neighbours
        }

    }

    data class Position4D(val x: Int, val y: Int, val z: Int, val t: Int): Position {

        override fun neighbours(): Set<Position4D> {
            val neighbours = mutableSetOf<Position4D>()

            for (dx in -1..1) {
                for (dy in -1..1) {
                    for (dz in -1..1) {
                        for (dt in -1..1) {
                            if (dx != 0 || dy != 0 || dz != 0 || dt != 0) {
                                neighbours.add(Position4D(x + dx, y + dy, z + dz, t + dt))
                            }
                        }
                    }
                }
            }

            return neighbours
        }

    }

}


package com.deviget.minesweeper.infra.config

import com.deviget.minesweeper.core.actions.RevealCell
import com.deviget.minesweeper.core.actions.StartGame
import com.deviget.minesweeper.core.domain.entities.BoardFactory
import com.deviget.minesweeper.core.domain.entities.DefaultBoardFactory
import com.deviget.minesweeper.core.domain.entities.DefaultMinerRandomizer
import com.deviget.minesweeper.core.domain.entities.MinerRandomizer
import com.deviget.minesweeper.core.domain.repositories.BoardIdRepository
import com.deviget.minesweeper.core.domain.repositories.BoardRepository
import com.deviget.minesweeper.infra.repositories.InMemoryBoardIdRepository
import com.deviget.minesweeper.infra.repositories.InMemoryBoardRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class Injector {

	@Bean
	open fun boardRepository(): BoardRepository = InMemoryBoardRepository()

	@Bean
	open fun boardIdRepository(): BoardIdRepository = InMemoryBoardIdRepository()

	@Bean
	open fun miner(): MinerRandomizer = DefaultMinerRandomizer

	@Bean
	open fun boardFactory(
			miner: MinerRandomizer,
			boardIdRepository: BoardIdRepository
	): BoardFactory = DefaultBoardFactory(miner, boardIdRepository)

	@Bean
	open fun startGame(
			boardRepository: BoardRepository,
			boardFactory: BoardFactory
	) = StartGame(boardRepository, boardFactory)

	@Bean
	open fun revealCell(boardRepository: BoardRepository) = RevealCell(boardRepository)

}
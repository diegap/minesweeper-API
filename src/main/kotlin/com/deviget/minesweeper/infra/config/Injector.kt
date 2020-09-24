package com.deviget.minesweeper.infra.config

import com.deviget.minesweeper.core.actions.GetBoardById
import com.deviget.minesweeper.core.actions.GetBoards
import com.deviget.minesweeper.core.actions.RevealCell
import com.deviget.minesweeper.core.actions.StartGame
import com.deviget.minesweeper.core.domain.entities.BoardFactory
import com.deviget.minesweeper.core.domain.entities.DefaultBoardFactory
import com.deviget.minesweeper.core.domain.entities.DefaultMinerRandomizer
import com.deviget.minesweeper.core.domain.entities.MinerRandomizer
import com.deviget.minesweeper.core.domain.exceptions.BoardExceptionVisitor
import com.deviget.minesweeper.core.domain.exceptions.BoardFinisher
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
	open fun boardFinisher(boardRepository: BoardRepository): BoardExceptionVisitor = BoardFinisher(boardRepository)

	@Bean
	open fun revealCell(
			boardRepository: BoardRepository,
			boardFinisher: BoardExceptionVisitor
	) = RevealCell(boardRepository, boardFinisher)

	@Bean
	open fun getBoards(boardRepository: BoardRepository) = GetBoards(boardRepository)

	@Bean
	open fun getBoardById(boardRepository: BoardRepository) = GetBoardById(boardRepository)

}
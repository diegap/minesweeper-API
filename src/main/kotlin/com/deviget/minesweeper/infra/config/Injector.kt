package com.deviget.minesweeper.infra.config

import com.deviget.minesweeper.core.actions.CreateUser
import com.deviget.minesweeper.core.actions.FlagCell
import com.deviget.minesweeper.core.actions.GetBoardById
import com.deviget.minesweeper.core.actions.GetBoards
import com.deviget.minesweeper.core.actions.GetUser
import com.deviget.minesweeper.core.actions.PauseBoard
import com.deviget.minesweeper.core.actions.QuestionMarkCell
import com.deviget.minesweeper.core.actions.ResumeBoard
import com.deviget.minesweeper.core.actions.RevealCell
import com.deviget.minesweeper.core.actions.StartGame
import com.deviget.minesweeper.core.domain.entities.board.BoardActionCommandName
import com.deviget.minesweeper.core.domain.entities.board.BoardExceptionVisitor
import com.deviget.minesweeper.core.domain.entities.board.BoardFactory
import com.deviget.minesweeper.core.domain.entities.board.BoardFinisher
import com.deviget.minesweeper.core.domain.entities.board.BoardStatusCommandName
import com.deviget.minesweeper.core.domain.entities.board.DefaultBoardFactory
import com.deviget.minesweeper.core.domain.entities.board.command.PauseBoardCommand
import com.deviget.minesweeper.core.domain.entities.board.command.ResumeBoardCommand
import com.deviget.minesweeper.core.domain.entities.cell.command.FlagActionCommand
import com.deviget.minesweeper.core.domain.entities.cell.command.QuestionMarkCommand
import com.deviget.minesweeper.core.domain.entities.cell.command.RevealActionCommand
import com.deviget.minesweeper.core.domain.entities.miner.DefaultMinerRandomizer
import com.deviget.minesweeper.core.domain.entities.miner.MinerRandomizer
import com.deviget.minesweeper.core.domain.repositories.BoardIdRepository
import com.deviget.minesweeper.core.domain.repositories.BoardRepository
import com.deviget.minesweeper.core.domain.repositories.UserRepository
import com.deviget.minesweeper.infra.repositories.InMemoryBoardIdRepository
import com.deviget.minesweeper.infra.repositories.InMemoryBoardRepository
import com.deviget.minesweeper.infra.repositories.InMemoryUserRepository
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
	open fun revealCell(boardFinisher: BoardExceptionVisitor) = RevealCell(boardFinisher)

	@Bean
	open fun flagCell(boardRepository: BoardRepository) = FlagCell(boardRepository)

	@Bean
	open fun questionMarkCell(boardRepository: BoardRepository) = QuestionMarkCell(boardRepository)

	@Bean
	open fun pauseBoard(boardRepository: BoardRepository) = PauseBoard(boardRepository)

	@Bean
	open fun resumeBoard(boardRepository: BoardRepository) = ResumeBoard(boardRepository)

	@Bean
	open fun getBoards(boardRepository: BoardRepository) = GetBoards(boardRepository)

	@Bean
	open fun getBoardById(boardRepository: BoardRepository) = GetBoardById(boardRepository)

	@Bean
	open fun userRepository(): UserRepository = InMemoryUserRepository()

	@Bean
	open fun createUser(userRepository: UserRepository) = CreateUser(userRepository)

	@Bean
	open fun getUser(userRepository: UserRepository) = GetUser(userRepository)

	@Bean
	open fun boardStatusCommandMap(
			pauseBoard: PauseBoard,
			resumeBoard: ResumeBoard
	) = mapOf(
			BoardStatusCommandName.PAUSE to PauseBoardCommand(pauseBoard),
			BoardStatusCommandName.RESUME to ResumeBoardCommand(resumeBoard)
	)

	@Bean
	open fun cellActionCommandMap(
			revealCell: RevealCell,
			flagCell: FlagCell,
			questionMarkCell: QuestionMarkCell
	) = mapOf(
			BoardActionCommandName.REVEAL to RevealActionCommand(revealCell),
			BoardActionCommandName.FLAG to FlagActionCommand(flagCell),
			BoardActionCommandName.QUESTION to QuestionMarkCommand(questionMarkCell)
	)

}
package com.deviget.minesweeper.infra.config

import com.deviget.minesweeper.core.actions.board.GetBoardById
import com.deviget.minesweeper.core.actions.board.GetBoards
import com.deviget.minesweeper.core.actions.board.PauseBoard
import com.deviget.minesweeper.core.actions.board.ResumeBoard
import com.deviget.minesweeper.core.actions.board.StartGame
import com.deviget.minesweeper.core.actions.cell.FlagCell
import com.deviget.minesweeper.core.actions.cell.QuestionMarkCell
import com.deviget.minesweeper.core.actions.cell.RevealCell
import com.deviget.minesweeper.core.actions.cell.UnflagCell
import com.deviget.minesweeper.core.actions.cell.UnquestionMarkCell
import com.deviget.minesweeper.core.actions.user.CreateUser
import com.deviget.minesweeper.core.actions.user.GetUser
import com.deviget.minesweeper.core.domain.entities.board.BoardExceptionVisitor
import com.deviget.minesweeper.core.domain.entities.board.BoardFactory
import com.deviget.minesweeper.core.domain.entities.board.BoardFinisher
import com.deviget.minesweeper.core.domain.entities.board.DefaultBoardFactory
import com.deviget.minesweeper.core.domain.entities.board.command.BoardStatusCommand
import com.deviget.minesweeper.core.domain.entities.board.command.PauseBoardCommand
import com.deviget.minesweeper.core.domain.entities.board.command.ResumeBoardCommand
import com.deviget.minesweeper.core.domain.entities.cell.command.CellCommand
import com.deviget.minesweeper.core.domain.entities.cell.command.FlagCommand
import com.deviget.minesweeper.core.domain.entities.cell.command.QuestionMarkCommand
import com.deviget.minesweeper.core.domain.entities.cell.command.RevealCommand
import com.deviget.minesweeper.core.domain.entities.cell.command.UnflagCellCommand
import com.deviget.minesweeper.core.domain.entities.cell.command.UnquestionMarkCommand
import com.deviget.minesweeper.core.domain.entities.miner.DefaultMinerRandomizer
import com.deviget.minesweeper.core.domain.entities.miner.MinerRandomizer
import com.deviget.minesweeper.core.domain.repositories.BoardIdRepository
import com.deviget.minesweeper.core.domain.repositories.BoardRepository
import com.deviget.minesweeper.core.domain.repositories.UserRepository
import com.deviget.minesweeper.infra.repositories.InMemoryBoardIdRepository
import com.deviget.minesweeper.infra.repositories.InMemoryBoardRepository
import com.deviget.minesweeper.infra.repositories.InMemoryUserRepository
import com.deviget.minesweeper.infra.rest.controllers.BoardStatusCommandName
import com.deviget.minesweeper.infra.rest.controllers.BoardStatusCommandToAction
import com.deviget.minesweeper.infra.rest.controllers.CellCommandName
import com.deviget.minesweeper.infra.rest.controllers.CellCommandToAction
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
	open fun unflagCell(boardRepository: BoardRepository) = UnflagCell(boardRepository)

	@Bean
	open fun questionMarkCell(boardRepository: BoardRepository) = QuestionMarkCell(boardRepository)

	@Bean
	open fun unquestionMarkCell(boardRepository: BoardRepository) = UnquestionMarkCell(boardRepository)

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
			unflagCell: UnflagCell,
			questionMarkCell: QuestionMarkCell,
			unQuestionMarkCell: UnquestionMarkCell
	) = mapOf(
			CellCommandName.REVEAL to RevealCommand(revealCell),
			CellCommandName.FLAG to FlagCommand(flagCell),
			CellCommandName.UNFLAG to UnflagCellCommand(unflagCell),
			CellCommandName.QUESTION to QuestionMarkCommand(questionMarkCell),
			CellCommandName.UNQUESTION to UnquestionMarkCommand(unQuestionMarkCell)
	)

	@Bean
	open fun cellCommand(cellCommandMap: Map<CellCommandName, CellCommand>) =
			CellCommandToAction(cellCommandMap)

	@Bean
	open fun statusCommand(boardStatusCommandMap: Map<BoardStatusCommandName, BoardStatusCommand>) =
			BoardStatusCommandToAction(boardStatusCommandMap)
}
package com.jslsolucoes.metrosp.pub.api;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.jslsolucoes.metrosp.pub.api.domain.MachineLearningUseCase;
import com.jslsolucoes.metrosp.pub.api.domain.MachineLearningUseCase.TrainModel;
import com.jslsolucoes.metrosp.pub.api.domain.MachineLearningUseCase.TrainModelEntry;
import com.jslsolucoes.metrosp.pub.api.domain.model.TaskCategory;
import com.jslsolucoes.metrosp.pub.api.domain.repo.TaskCategoryRepo;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner loadData(TaskCategoryRepo taskCategoryRepo,
			MachineLearningUseCase machineLearningUseCase) {
		return (args) -> {
			taskCategoryRepo.saveAll(taskCategories());
			logger.info("Ask for train exchangeMachineLearningModel: {}",
					machineLearningUseCase.train(trainModel()).getStatus());
		};
	}

	private TrainModel trainModel() {
		Set<TrainModelEntry> dataSet = Set.of(
				new TrainModelEntry(
						"Queria fazer uma reclamacao, pois esta atrasando demais os trens da linha vermelha",
						"complains"),
				new TrainModelEntry("Teste de email de reclamacao", "complains"),
				new TrainModelEntry("Esta ocorrendo varios atrasos nas estacoes de metro", "complains"),
				new TrainModelEntry("Queria saber como faco para bloquear meu cartao do metro", "questions"),
				new TrainModelEntry("Como faco pra recarregar meu cartao ?", "questions"));
		return new TrainModel("exchangeMachineLearningModel", dataSet);
	}

	private List<TaskCategory> taskCategories() {
		return List.of(new TaskCategory("Ouvidoria", "complains"), new TaskCategory("Apoio ao usuário", "questions"));
	}
}
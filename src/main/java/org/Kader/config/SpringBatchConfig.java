package org.Kader.config;

import org.Kader.Entities.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;


@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {


    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                   ItemReader<User> itemReader, ItemProcessor<User, User> itemProcessor, ItemWriter<User> itemWriter){

        Step step = stepBuilderFactory.get("File-Load")
                                     .<User,User>chunk(100)
                                     .reader(itemReader)
                                     .processor(itemProcessor)
                                     .writer(itemWriter)
                                     .build();

        return jobBuilderFactory.get("Load-CSV ")
                        .incrementer(new RunIdIncrementer())
                        .start(step)
                        .build();
    }

    @Bean
    public FlatFileItemReader<User> itemReader(@Value("${input}") Resource ressource){

        FlatFileItemReader<User> fileItemReader=new FlatFileItemReader<>();
        fileItemReader.setResource(ressource);
        fileItemReader.setName("CSV-Reader");
        fileItemReader.setLinesToSkip(1);
        //flatFileItemReader.setStrict(false);
        fileItemReader.setLineMapper(lineMapper());
        return  fileItemReader;
    }

    @Bean
    public LineMapper<User>lineMapper(){

        DefaultLineMapper<User> defaultLineMapper=new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer=new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames(new String[]{"id","name","dept","salary"});
        lineTokenizer.setStrict(false);

        BeanWrapperFieldSetMapper<User> fieldSetMapper=new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(User.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        return  defaultLineMapper;
    }
}

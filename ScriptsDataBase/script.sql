USE [EstudiantesClase]
GO
/****** Object:  User [accesEstudiante]    Script Date: 29/09/2021 5:27:18 p. m. ******/
CREATE USER [accesEstudiante] FOR LOGIN [accesEstudiante] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  User [auth]    Script Date: 29/09/2021 5:27:18 p. m. ******/
CREATE USER [auth] FOR LOGIN [auth] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [accesEstudiante]
GO
/****** Object:  Table [dbo].[Estudiante]    Script Date: 29/09/2021 5:27:18 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Estudiante](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](100) NOT NULL,
	[email] [varchar](100) NOT NULL,
	[telefono] [varchar](20) NOT NULL,
 CONSTRAINT [PK_Estudiante] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
